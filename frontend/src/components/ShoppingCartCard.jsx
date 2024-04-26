import React from 'react';
import {Box, Typography} from "@mui/material";
import ShoppingCartList from "../components/ShoppingCartList";

export default function ShoppingCartCard({ sx, data, handleDelete, handleProceedToPayment, handleIncrementQuantity, handleDecrementQuantity}) {
    return (
        <Box sx={{
            display: 'flex',
            flexDirection: 'column',
            height: '80vh',
            width: '95vw',
            backgroundColor: 'info.light',
            color: 'secondary.light',
            borderRadius: '15px',
            padding: '1rem',
            ...sx
        }}>
            <Typography variant="h4" sx={{
                paddingBottom: '1rem'
            }}>
                Shopping Cart
            </Typography>
            <ShoppingCartList
                data={data}
                handleDelete={handleDelete}
                handleProceedToPayment={handleProceedToPayment}
                handleIncrementQuantity={handleIncrementQuantity}
                handleDecrementQuantity={handleDecrementQuantity}
            />
        </Box>
    );
}
