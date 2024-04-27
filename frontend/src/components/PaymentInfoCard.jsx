import React from 'react';
import { Box, Button, TextField, Typography } from "@mui/material";

export default function PaymentInfoCard({ firstName, lastName, creditCardNumber, expirationDate, setCreditCardNumber,
                                            setFirstName, setLastName, setExpirationDate, total, handlePlaceOrder, sx }) {
    const textFieldStyle = {
        paddingBottom: '2%',
        width: '100%',
    };

    const formatCurrency = (amount) => {
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        }).format(amount);
    };


    return (
        <Box sx={{
            display: 'flex',
            flexDirection: 'column',
            backgroundColor: 'info.light',
            color: 'secondary.light',
            borderRadius: '15px',
            padding: '1rem 3rem 1rem 3rem',
            ...sx
        }}>
            <Typography variant='h4' sx={{ alignSelf: 'center' }}>
                Payment Details
            </Typography>
            <Typography>Card Number</Typography>
            <TextField
                type="text"
                value={creditCardNumber}
                onChange={(e) => setCreditCardNumber(e.target.value)}
                sx={textFieldStyle}
            />
            <Typography>First Name</Typography>
            <TextField
                type="text"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                sx={textFieldStyle}
            />
            <Typography>Last Name</Typography>
            <TextField
                type="text"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                sx={textFieldStyle}
            />
            <Typography>Expiration Date</Typography>
            <TextField
                type="date"
                value={expirationDate}
                onChange={(e) => setExpirationDate(e.target.value)}
                sx={textFieldStyle}
                InputLabelProps={{ shrink: true }}
            />
            <Typography sx={{ alignSelf: 'flex-end', marginTop: 'auto', marginBottom: 2 }}>
                Total: {formatCurrency(total)}
            </Typography>
            <Button
                onClick={handlePlaceOrder}
                sx={{
                    borderRadius: '20px',
                    backgroundColor: '#FF907E',
                    color: 'secondary.light',
                    fontWeight: 'bold',
                    fontSize: '1.2rem',
                    '&:hover': {
                        backgroundColor: 'primary.main',
                    }
                }}>
                Place Order
            </Button>
        </Box>
    );
}
