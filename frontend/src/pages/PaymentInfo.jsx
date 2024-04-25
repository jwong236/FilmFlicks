import React, { useState } from 'react';
import { Box } from "@mui/material";
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import PaymentInfoCard from '../components/PaymentInfoCard.jsx';

export default function PaymentInfo({ total }) {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [creditCardNumber, setCreditCardNumber] = useState('');
    const [expirationDate, setExpirationDate] = useState('');

    const handlePlaceOrder = async () => {
        console.log("Order Details:");
        console.log("First Name:", firstName);
        console.log("Last Name:", lastName);
        console.log("Credit Card Number:", creditCardNumber);
        console.log("Expiration Date:", expirationDate);
        console.log("Total:", total);
    };

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
                />
            </Background>
        </Box>
    );
}
