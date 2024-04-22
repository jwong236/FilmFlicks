import React, { useState } from 'react';
import {Box, Container, TextField, Button } from "@mui/material";

const HOST = import.meta.env.VITE_HOST;

export default function PaymentInfo() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [creditCardNumber, setCreditCardNumber] = useState('');
    const [expiration, setExpiration] = useState('');
    const [total, setTotal] = useState(0);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            // Perform validation if needed

            // Send payment data to server
            const response = await fetch(`http://${HOST}:8080/fabFlix/payment`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    firstName,
                    lastName,
                    creditCardNumber,
                    expiration,
                    total
                }),
                credentials: 'include'
            });
            if (!response.ok) {
                console.error('Payment failed');
                return;
            }

            if (response.status === 200){
                console.log("PAYMENT SUCCESS!");
            }else{
                console.error('Payment failed from backend');
            }
        } catch (error) {
            console.error('Error processing payment: ', error);
        }
    };

    return (
        <Container>
            <Box mt={4}>
                <form onSubmit={handleSubmit}>
                    <TextField
                        label="First Name"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required

                    />
                    <TextField
                        label="Last Name"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                    />
                    <TextField
                        label="Credit Card Number"
                        value={creditCardNumber}
                        onChange={(e) => setCreditCardNumber(e.target.value)}
                        required
                    />
                    <TextField
                        label="Expiration Date"
                        type="date"
                        value={expiration}
                        onChange={(e) => setExpiration(e.target.value)}
                        required
                        InputLabelProps={{ shrink: true }}
                    />
                    <Button type="submit">Submit Payment</Button>
                </form>
            </Box>
        </Container>
    );
}
