import React from 'react';
import { Box } from "@mui/material";
import Navbar from "../components/common/navbar/Navbar.jsx";
import Background from "../components/components(deprecated)/Background.jsx";
import ConfirmationCard from "../components/confirmationpage/ConfirmationCard.jsx";

export default function ConfirmationPage() {
    return (
        <Box
            sx={{
                display: 'flex',
                height: '100vh',
                width: '100vw',
                flexDirection: 'column',
            }}
        >
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center' }}>
                <ConfirmationCard />
            </Background>
        </Box>
    );
}
