import { Box } from '@mui/material';

function FullHeightContainer({ children, sx, ...props }) {
    return (
        <Box
            sx={{
                display: 'flex',
                width: '100%',
                height: '100%',
                ...sx,
            }}
            {...props}
        >
            {children}
        </Box>
    );
}

export default FullHeightContainer;
