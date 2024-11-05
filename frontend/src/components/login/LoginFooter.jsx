// src/components/LoginFooter.jsx
import React from 'react';
import { Box, Typography } from '@mui/material';

export default function LoginFooter() {
    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                width: '100%',
                flexGrow: '1',
                alignItems: 'center',
                justifyContent: 'center',
            }}
        >
            <Typography sx={{ color: 'secondary.light' }}>UCI CS122b</Typography>
            <Typography sx={{ color: 'secondary.light' }}>Created by Andy Phu</Typography>
            <Typography sx={{ color: 'secondary.light' }}>and Jacob Wong</Typography>
        </Box>
    );
}