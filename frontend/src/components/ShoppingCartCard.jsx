import React from 'react';
import {Box, Typography} from "@mui/material";
import ShoppingCartList from "../components/ShoppingCartList";

<<<<<<< HEAD
export default function ShoppingCartCard({ sx, data, total, handleDelete, handleProceedToPayment}) {
=======
export default function ShoppingCartCard({ sx, data, handleDelete, handleProceedToPayment, handleIncrementQuantity, handleDecrementQuantity}) {
>>>>>>> c424c04a0daf22ae05c0cf0464e9025d13b80ad7
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
                total={total}
                handleDelete={handleDelete}
                handleProceedToPayment={handleProceedToPayment}
                handleIncrementQuantity={handleIncrementQuantity}
                handleDecrementQuantity={handleDecrementQuantity}
            />
        </Box>
    );
}
