import React from 'react';
import { Box, Typography } from "@mui/material";
import ShoppingCartList from "./ShoppingCartList.jsx";

export default function ShoppingCartCard({
                                             sx,
                                             cartData,
                                             totalAmount,
                                             onDeleteItem,
                                             onProceedToPayment,
                                             onIncrementItem,
                                             onDecrementItem
                                         }) {
    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                height: '80vh',
                width: '95vw',
                backgroundColor: 'info.light',
                color: 'secondary.light',
                borderRadius: '15px',
                padding: '1rem',
                ...sx
            }}
        >
            <Typography
                variant="h4"
                sx={{ paddingBottom: '1rem' }}
            >
                Shopping Cart
            </Typography>
            {cartData && cartData.length > 0 ? (
                <ShoppingCartList
                    cartData={cartData}
                    totalAmount={totalAmount}
                    onDeleteItem={onDeleteItem}
                    onProceedToPayment={onProceedToPayment}
                    onIncrementItem={onIncrementItem}
                    onDecrementItem={onDecrementItem}
                />
            ) : (
                <Typography variant="h6" sx={{ color: 'text.secondary', paddingTop: '2rem' }}>
                    Your cart is empty.
                </Typography>
            )}
        </Box>
    );
}
