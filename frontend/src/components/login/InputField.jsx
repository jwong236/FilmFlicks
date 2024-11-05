// src/components/login/InputField.jsx
import React from 'react';
import { Box, Typography, TextField } from '@mui/material';

export default function InputField({ label, value, onChange, type = 'text' }) {
    return (
        <Box>
            <Typography sx={{ color: 'secondary.light', marginLeft: '5%', paddingTop: '2%' }}>
                {label}
            </Typography>
            <TextField
                type={type}
                value={value}
                onChange={onChange}
                sx={{ width: '100%', paddingLeft: '5%', paddingRight: '5%' }}
            />
        </Box>
    );
}