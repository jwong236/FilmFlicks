import React from 'react';
import { Box, Typography, useTheme} from "@mui/material";
import Navbar from "../components/Navbar.jsx";
import Background from "../components/Background.jsx";
import ConfirmationCard from "../components/ConfirmationCard.jsx";
import { useLocation } from 'react-router-dom';

export default function Confirmation() { // Put orderDetails back between the {} later
    const theme = useTheme();
    const orderDetails = location.state;
    console.log("order details: " + orderDetails);
    // const orderDetails = [
    //     { saleId: 101, title: "Inception", quantity: 2, price: 10.00, total: 20.00 },
    //     { saleId: 102, title: "The Matrix", quantity: 1, price: 15.00, total: 15.00 },
    //     { saleId: 103, title: "Interstellar", quantity: 1, price: 12.50, total: 12.50 },
    //     { saleId: 104, title: "Blade Runner 2049", quantity: 1, price: 9.99, total: 9.99 }
    // ];
    return (
        <Box sx={{
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center'}}>
                <ConfirmationCard data={orderDetails} sx={{
                    height: '80vh'
                }}/>
            </Background>
        </Box>
    );
}
