import React from 'react';
import {Box, Typography} from "@mui/material";
import ShoppingCartList from "../components/ShoppingCartList";

const defaultData = [
    { title: "Inception", quantity: 1, price: 10.00, total: 10.00 },
    { title: "The Matrix", quantity: 2, price: 15.00, total: 30.00 }
];

export default function ShoppingCartCard({ sx }) {
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
            <ShoppingCartList data={defaultData} />
        </Box>
    );
}
