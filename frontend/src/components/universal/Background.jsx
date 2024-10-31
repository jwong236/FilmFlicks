import React from 'react';
import { Box} from "@mui/material";

import popcorn from "../../assets/popcorn.png";

const Background = ({ children, sx }) => {
    return (
        <Box sx={{
            display: 'flex',
            flexGrow: 1,
            backgroundColor: '#f1f1f1',
            backgroundImage: `url(${popcorn})`,
            backgroundSize: 'cover',
            backgroundRepeat: 'no-repeat',
            ...sx
        }}>
            {children}
        </Box>
    );
}
export default Background;