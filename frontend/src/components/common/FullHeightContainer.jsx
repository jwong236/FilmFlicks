import { Box } from '@mui/material';
function FullHeightContainer({ children, sx, ...props }) {
    return (
        <Box
            sx={{
                display: 'flex',
                height: '100vh',
                width: '100vw',
                ...sx,
            }}
            {...props}
        >
            {children}
        </Box>
    );
}

export default FullHeightContainer;
