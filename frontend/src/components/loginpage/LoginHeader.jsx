// src/components/login/LoginHeader.jsx
import React from 'react';
import { Box, Typography } from '@mui/material';

export default function LoginHeader() {
    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                justifyContent: 'center',
                width: '100%',
                paddingTop: '5%',
                paddingBottom: '2%',
            }}
        >
            <Typography variant="h1" component="h1" color="secondary.light" style={{ letterSpacing: 3 }}>
                FabFlix
            </Typography>
            <Typography variant="h3" component="h3" color="secondary.light">
                Login
            </Typography>
        </Box>
    );
}