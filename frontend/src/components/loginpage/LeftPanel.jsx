// src/components/login/LeftPanel.jsx
import React from 'react';
import { Box } from '@mui/material';
import singlepopcorn from '../../assets/singlepopcorn.png';

export default function LeftPanel() {
    return (
        <Box
            sx={{
                display: 'flex',
                backgroundColor: '#FFFFF4',
                height: '100vh',
                flex: '2',
                backgroundImage: `url(${singlepopcorn})`,
                backgroundRepeat: 'no-repeat',
                backgroundPosition: 'center',
            }}
        />
    );
}