import React from 'react';
import { Box, Typography } from "@mui/material";
import ShoppingCartList from "./ShoppingCartList.jsx";
import {useShoppingCartPageHooks} from "../../hooks/useShoppingCartPageHooks.jsx";

export default function ShoppingCartCard() {
    const {
        cartData,
        total,
        incrementItem,
        decrementItem,
        deleteItem,
        handleProceedToPayment
    } = useShoppingCartPageHooks();
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
                    totalAmount={total}
                    onDeleteItem={deleteItem}
                    onProceedToPayment={handleProceedToPayment}
                    onIncrementItem={incrementItem}
                    onDecrementItem={decrementItem}
                />
            ) : (
                <Typography variant="h6" sx={{ color: 'text.secondary', paddingTop: '2rem' }}>
                    Your cart is empty.
                </Typography>
            )}
        </Box>
    );
}
