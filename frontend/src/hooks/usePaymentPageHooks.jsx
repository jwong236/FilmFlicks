import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const URL = import.meta.env.VITE_BACKEND_URL;

export const usePaymentPageHooks = () => {
    const [paymentInfo, setPaymentInfo] = useState({
        firstName: '',
        lastName: '',
        creditCardNumber: '',
        expirationDate: '',
    });
    const [results, setResults] = useState("");
    const navigate = useNavigate();

    const handlePlaceOrder = async () => {
        try {
            const response = await fetch(`${URL}/payment`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(paymentInfo),
                credentials: 'include'
            });

            if (!response.ok) {
                if (response.status === 401) navigate('/login');
                else if (response.status === 406) setResults("Credit Card Information Not Found");
                return;
            }

            const jsonData = await response.json();
            navigate('/confirmation', { state: { orderDetails: jsonData } });
        } catch (error) {
            console.error('Failed to process payment:', error);
        }
    };

    return { paymentInfo, setPaymentInfo, handlePlaceOrder, results };
};
