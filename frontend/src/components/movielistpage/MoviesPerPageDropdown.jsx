import React from 'react';
import { FormControl, Select, MenuItem, Typography } from "@mui/material";

const MoviesPerPageDropdown = ({ pageSize, setPageSize }) => {
    const handlePageDropDown = (event) => {
        setPageSize(event.target.value);
    };

    return (
        <FormControl variant="outlined" size="small">
            <Typography sx={{ color: 'primary.dark' }}>Movies Per Page:</Typography>
            <Select
                value={pageSize}
                onChange={handlePageDropDown}
                sx={{ minWidth: '5rem' }}
            >
                <MenuItem value={10}>10</MenuItem>
                <MenuItem value={25}>25</MenuItem>
                <MenuItem value={50}>50</MenuItem>
                <MenuItem value={100}>100</MenuItem>
            </Select>
        </FormControl>
    );
};

export default MoviesPerPageDropdown;
