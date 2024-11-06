import React from 'react';
import { Box, Button, TextField, Typography } from "@mui/material";

export default function PaymentInfoCard({ paymentInfo, setPaymentInfo, total, handlePlaceOrder, sx, results }) {
    const textFieldStyle = { paddingBottom: '2%', width: '100%' };

    const formatCurrency = (amount) => new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    }).format(amount);

    const handleChange = (field) => (e) => {
        setPaymentInfo((prev) => ({ ...prev, [field]: e.target.value }));
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
            <Typography variant='h4' sx={{ alignSelf: 'center', marginBottom: '30px', marginTop: '30px' }}>
                Payment Details
            </Typography>
            <Typography>Card Number</Typography>
            <TextField
                type="text"
                value={paymentInfo.creditCardNumber}
                onChange={handleChange('creditCardNumber')}
                sx={textFieldStyle}
            />
            <Typography>First Name</Typography>
            <TextField
                type="text"
                value={paymentInfo.firstName}
                onChange={handleChange('firstName')}
                sx={textFieldStyle}
            />
            <Typography>Last Name</Typography>
            <TextField
                type="text"
                value={paymentInfo.lastName}
                onChange={handleChange('lastName')}
                sx={textFieldStyle}
            />
            <Typography>Expiration Date</Typography>
            <TextField
                type="date"
                value={paymentInfo.expirationDate}
                onChange={handleChange('expirationDate')}
                sx={textFieldStyle}
                InputLabelProps={{ shrink: true }}
            />
            <Typography sx={{ alignSelf: 'flex-center', marginTop: 'auto', marginBottom: 2, fontSize: '20px' }}>
                {results}
            </Typography>
            <Typography sx={{ alignSelf: 'flex-end', marginTop: 'auto', marginBottom: 2, fontSize: '25px' }}>
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
                    '&:hover': { backgroundColor: 'primary.main' }
                }}
            >
                Place Order
            </Button>
        </Box>
    );
}
