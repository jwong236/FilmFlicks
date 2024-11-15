import React from 'react';
import { Box, Button, TextField, Typography } from "@mui/material";
import { useLocation } from "react-router-dom";
import { usePaymentPageHooks } from "../../hooks/usePaymentPageHooks.jsx";

export default function PaymentInfoCard() {
    const location = useLocation();
    const { cartData, total = 0 } = location.state || {};

    const {
        paymentInfo = {},
        setPaymentInfo,
        handlePlaceOrder,
        results,
    } = usePaymentPageHooks();

    const formatCurrency = (amount = 0) =>
        new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(amount);

    const handleChange = (field) => (e) => {
        setPaymentInfo((prev) => ({ ...prev, [field]: e.target.value }));
    };

    const fields = [
        { label: "Card Number", type: "text", field: "id" },
        { label: "First Name", type: "text", field: "firstName" },
        { label: "Last Name", type: "text", field: "lastName" },
        { label: "Expiration Date", type: "date", field: "expiration" },
    ];

    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                backgroundColor: 'info.light',
                color: 'secondary.light',
                borderRadius: '15px',
                padding: '1rem 3rem',
                height: '80vh',
                width: '30vw',
            }}
        >
            <Typography variant='h4' sx={{ alignSelf: 'center', margin: '30px 0' }}>
                Payment Details
            </Typography>
            {fields.map(({ label, type, field }) => (
                <div key={field}>
                    <Typography>{label}</Typography>
                    <TextField
                        type={type}
                        value={paymentInfo[field] || ''}
                        onChange={handleChange(field)}
                        sx={{ paddingBottom: '2%', width: '100%' }}
                        InputLabelProps={type === 'date' ? { shrink: true } : undefined}
                    />
                </div>
            ))}
            <Typography sx={{ marginTop: 'auto', marginBottom: 2, fontSize: '20px' }}>
                {results}
            </Typography>
            <Typography sx={{ alignSelf: 'flex-end', marginBottom: 2, fontSize: '25px' }}>
                Total: {formatCurrency(total)}
            </Typography>
            <Button
                onClick={() => handlePlaceOrder(cartData, total)}
                sx={{
                    borderRadius: '20px',
                    backgroundColor: '#FF907E',
                    color: 'secondary.light',
                    fontWeight: 'bold',
                    fontSize: '1.2rem',
                    '&:hover': { backgroundColor: 'primary.main' },
                }}
            >
                Place Order
            </Button>
        </Box>
    );
}
