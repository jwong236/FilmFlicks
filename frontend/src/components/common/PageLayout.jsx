import React from 'react';
import FullHeightContainer from './FullHeightContainer.jsx';
import Navbar from './Navbar.jsx';
import popcorn from '../../assets/popcorn.png';
import { Box } from '@mui/material';

function PageLayout({ children, sx, ...props }) {
    return (
        <FullHeightContainer
            sx={{
                flexDirection: 'column',
                minHeight: '100vh',
                width: '100vw',
                backgroundImage: `url(${popcorn})`,
                backgroundSize: '100% auto',
                backgroundRepeat: 'repeat-y',
                backgroundPosition: 'center 4rem',
                ...sx,
            }}
            {...props}
        >
            <Navbar />
            <Box
                sx={{
                    flexGrow: 1,
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    paddingTop: '4rem',
                }}
            >
                {children}
            </Box>
        </FullHeightContainer>
    );
}

export default PageLayout;
