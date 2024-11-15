import React from 'react';
import { Box, Typography, Button } from "@mui/material";
import { useNavigate, useLocation } from 'react-router-dom';
import ConfirmationCardTable from "./ConfirmationCardTable.jsx";

export default function ConfirmationCard() {
    const navigate = useNavigate();
    const location = useLocation();

    const { cartData = [], total = 0, saleData = []} = location.state || {};

    const handleBackClick = () => {
        navigate('/');
    };

    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                backgroundColor: 'secondary.light',
                color: 'secondary.contrastText',
                borderRadius: '15px',
                padding: '1rem 3rem',
                alignItems: 'center',
            }}
        >
            <Typography variant='h4' sx={{ color: 'primary.main', fontWeight: 'bold', padding: '1rem' }}>
                Confirmation
            </Typography>
            <ConfirmationCardTable cartData={cartData} saleData = {saleData} />
            <Typography variant="h6" sx={{ marginY: '20px', fontWeight: 'bold', color: 'primary.main' }}>
                Grand Total: ${total}
            </Typography>
            <Button
                onClick={handleBackClick}
                sx={{
                    alignSelf: 'flex-end',
                    fontWeight: 'bold',
                    marginTop: 'auto',
                    backgroundColor: 'info.light',
                    '&:hover': {
                        backgroundColor: 'info.dark',
                    },
                }}
            >
                Back To Homepage
            </Button>
        </Box>
    );
}
