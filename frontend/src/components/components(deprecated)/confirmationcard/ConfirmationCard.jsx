import React from 'react';
import { Box, Typography, Button } from "@mui/material";
import ConfirmationCardTable from "./ConfirmationCardTable.jsx";
import { useNavigate } from 'react-router-dom';

export default function ConfirmationCard({ data, sx }) {
    const navigate = useNavigate();

    const grandTotal = data.reduce((acc, item) => acc + item.total, 0);

    const handleBackClick = () => {
        navigate('/');
    };

    return (
        <Box sx={{
            display: 'flex',
            flexDirection: 'column',
            backgroundColor: 'secondary.light',
            color: 'secondary.contrastText',
            borderRadius: '15px',
            padding: '1rem 3rem 1rem 3rem',
            alignItems: 'center',
            ...sx
        }}>
            <Typography variant='h4' sx={{
                color: 'primary.main',
                fontWeight: 'bold',
                padding: '1rem'
            }}>
                Confirmation
            </Typography>
            <ConfirmationCardTable data={data} />
            <Typography variant="h6" sx={{ marginY: '20px', fontWeight: 'bold', color: 'primary.main' }}>
                Grand Total: ${grandTotal.toFixed(2)}
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
                    }
                }}>
                Back To Homepage
            </Button>
        </Box>
    );
}
