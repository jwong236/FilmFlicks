import React from 'react';
import {Box, Button, Typography} from "@mui/material";

export default function DashboardAddMovieCard(){
    return(
        <Box sx={{
            display: 'flex',
            height: '80vh',
            width: '50vw',
            flexDirection: 'column',
            backgroundColor: 'info.light',
            borderRadius: '20px',
            alignItems: 'center',
            padding: '1rem'
        }}>
            <Typography variant='h3' sx={{color: 'primary.dark'}}>
                Employee Dashboard
            </Typography>
        </Box>
    );
}