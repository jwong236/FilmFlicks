import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const URL = import.meta.env.VITE_BACKEND_URL;

export const usePaymentPageHooks = () => {
    const [paymentInfo, setPaymentInfo] = useState({
        id: '',
        firstName: '',
        lastName: '',
        expiration: '',
    });

    const [results, setResults] = useState("");
    const navigate = useNavigate();

    const handlePlaceOrder = async (cartData, total) => {
        try {
            // Step 1: Process payment
            const paymentResponse = await fetch(`${URL}/transaction/payment`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(paymentInfo),
                credentials: 'include',
            });

            if (!paymentResponse.ok) {
                if (paymentResponse.status === 401) {
                    navigate('/login');
                } else {
                    const errorData = await paymentResponse.json();
                    setResults(errorData.error || "An unknown error occurred.");
                }
                return;
            }

            // Step 2: Fetch customer data from the session
            const sessionResponse = await fetch(`${URL}/metadata/session`, {
                credentials: 'include',
            });

            if (!sessionResponse.ok) {
                if (sessionResponse.status === 401) {
                    navigate('/login');
                } else {
                    const errorData = await sessionResponse.json();
                    setResults(errorData.error || "Failed to retrieve customer data.");
                }
                return;
            }

            const sessionData = await sessionResponse.json();
            const customerId = sessionData.customer?.id;

            if (!customerId) {
                setResults("Customer information is missing in the session.");
                return;
            }

            // Step 3: Create sales for each item in the cart
            const saleData = [];
            const currentDate = new Date().toISOString().split("T")[0]; // Format as YYYY-MM-DD

            for (const item of cartData) {
                const saleResponse = await fetch(
                    `${URL}/database/sale/add?customerId=${customerId}&movieId=${encodeURIComponent(item.id)}&saleDate=${currentDate}`,
                    {
                        method: 'POST',
                        credentials: 'include',
                    }
                );


                if (!saleResponse.ok) {
                    const errorData = await saleResponse.json();
                    setResults(errorData.error || "Failed to create sale.");
                    return;
                }

                const sale = await saleResponse.json();
                saleData.push(sale);
            }

            // Step 4: Navigate to confirmation page with cartData, total, and saleData
            navigate('/confirmation', { state: { cartData, total, saleData } });
        } catch (error) {
            setResults(
                `An error occurred: ${
                    error.message || "Failed to process payment or retrieve sale data."
                } Please try again later.`
            );
        }
    };


    return { paymentInfo, setPaymentInfo, handlePlaceOrder, results };
};
