import React, { useEffect, useState } from 'react';
import { Box, Container } from "@mui/material";
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import ShoppingCartCard from '../components/ShoppingCartCard.jsx';

const HOST = import.meta.env.VITE_HOST;

export default function ShoppingCart() {

    return (
        <Box sx={{
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center'}}>
                <ShoppingCartCard />
            </Background>
        </Box>

    );
}
