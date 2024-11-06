import React from 'react';
import Navbar from './navbar/Navbar.jsx';
import popcorn from '../../assets/popcorn.png';
import { Box } from '@mui/material';

function PageLayout({ children, sx, ...props }) {
    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                width: '100vw',
                maxWidth: '100%',
                minHeight: '100vh',
                backgroundImage: `url(${popcorn})`,
                backgroundSize: '100%',
                backgroundRepeat: 'repeat-y',
                backgroundPosition: 'center 4rem',
            }}
            {...props}
        >
            <Navbar />
            <Box
                sx={{
                    display: 'flex',
                    flexGrow: 1,
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    ...sx,
                }}
            >
                {children}
            </Box>
        </Box>
    );
}

export default PageLayout;
