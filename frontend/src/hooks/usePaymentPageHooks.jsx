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

            /*// Step 2: Fetch sale data from the /sale/add endpoint
            const saleResponse = await fetch(`${URL}/sale/add`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ cartData }), // Pass cartData for the sale
                credentials: 'include',
            });

            if (!saleResponse.ok) {
                const errorData = await saleResponse.json();
                setResults(errorData.error || "Failed to retrieve sale data.");
                return;
            }

            const saleData = await saleResponse.json(); // Sale data returned from the endpoint*/

            // Step 3: Navigate to confirmation page with cartData, total, and saleData
            const saleData = [
                { id: 1, customer_id: 101, movie_id: 5, sale_date: "2024-01-01" },
                { id: 2, customer_id: 101, movie_id: 6, sale_date: "2024-01-01" }
            ];
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
