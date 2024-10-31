import React from 'react';
import { Box, Typography } from "@mui/material";
import Navbar from "../components/components(deprecated)/Navbar.jsx";
import Background from "../components/universal/Background.jsx";
import ConfirmationCard from "../components/components(deprecated)/ConfirmationCard.jsx";
import { useLocation } from 'react-router-dom';

export default function Confirmation() {
    const location = useLocation();
    const orderDetailsObject = location.state ? location.state.orderDetails : {};

    const orderDetailsArray = Object.keys(orderDetailsObject).map((title, index) => {
        const details = orderDetailsObject[title];
        return {
            saleId: 1000 + index, // Generate a default saleId
            title: title,
            quantity: details.quantity,
            price: details.price,
            total: details.total
        };
    });

    console.log("Order details:", orderDetailsArray);

    return (
        <Box sx={{
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center' }}>
                <ConfirmationCard data={orderDetailsArray} sx={{ height: '80vh' }} />
            </Background>
        </Box>
    );
}
