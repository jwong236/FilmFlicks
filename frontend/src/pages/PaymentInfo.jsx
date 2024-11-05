import React, {useEffect, useState} from 'react';
import { Box } from "@mui/material";
import Navbar from '../components/common/Navbar.jsx';
import Background from '../components/components(deprecated)/Background.jsx';
import PaymentInfoCard from '../components/components(deprecated)/PaymentInfoCard.jsx';
import {useNavigate} from "react-router-dom";


const URL = import.meta.env.VITE_URL;


export default function PaymentInfo() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [creditCardNumber, setCreditCardNumber] = useState('');
    const [expirationDate, setExpirationDate] = useState('');
    const navigate = useNavigate();
    const [total, setTotal] = useState(0);
    const [results, setResults] = useState("");
    const handlePlaceOrder = async () => {
        try {
            const response = await fetch(`${URL}/payment`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    firstName: firstName,
                    lastName: lastName,
                    creditCardNumber: creditCardNumber,
                    expirationDate: expirationDate
                }),
                credentials: 'include'
            });

            if (!response.ok) {
                const errorText = await response.text();
                console.error('Error response status:', response.status, 'Error message:', errorText);
                if (response.status === 401) {
                    console.log("User not logged in, redirecting to login page.");
                    navigate('/login');
                } else if (response.status === 406) {
                    console.error("Invalid credit card information.");
                    setResults("Credit Card Information Not Found");
                }
                return;
            }
            const jsonData = await response.json();
            console.log("Payment successful, received response:", jsonData);
            navigate('/confirmation', { state: { orderDetails: jsonData } });
        } catch (error) {
            console.error('Failed to process payment:', error);
        }
    };


    async function totalPrice() {
        try {
            console.log("attempting to get total price");
            const response = await fetch(`${URL}/totalPrice`,{
                credentials: 'include'
            });
            if(response.status === 401){
                console.log("cant access payment info before logging in");
                navigate('/login');
            }else{
                const data = await response.json();
                console.log("TOTAL : " + data.total);
                setTotal(data.total);
            }

        } catch (error) {
            console.error('Error Calculating Total Price: ', error);
        }
    }

    useEffect(() => {
        try{
            totalPrice()
        }catch(error){
            console.error('Error handling totalPrice: ', error);

        }
    }, []);

    return (
        <Box sx={{
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center'}}>
                <PaymentInfoCard
                    firstName={firstName}
                    lastName={lastName}
                    creditCardNumber={creditCardNumber}
                    expirationDate={expirationDate}
                    setFirstName={setFirstName}
                    setLastName={setLastName}
                    setCreditCardNumber={setCreditCardNumber}
                    setExpirationDate={setExpirationDate}
                    total={total}
                    handlePlaceOrder={handlePlaceOrder}
                    sx={{ height: '80vh', width: '30vw' }}
                    results = {results}
                />
            </Background>
        </Box>
    );
}
