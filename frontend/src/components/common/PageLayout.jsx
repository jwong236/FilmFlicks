import React from 'react';
import FullHeightContainer from './FullHeightContainer.jsx';
import Navbar from './Navbar.jsx';
import popcorn from '../../assets/popcorn.png';
import { Box } from '@mui/material';

function PageLayout({ children, sx, ...props }) {
    return (
        <FullHeightContainer sx={{ flexDirection: 'column', ...sx }} {...props}>
            <Navbar />
            <Box
                sx={{
                    flexGrow: 1,
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    backgroundImage: `url(${popcorn})`,
                    backgroundSize: 'cover',
                    backgroundRepeat: 'no-repeat',
                }}
            >
                {children}
            </Box>
        </FullHeightContainer>
    );
}

export default PageLayout;