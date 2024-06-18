import React from 'react';
import { FormControl, Select, MenuItem, Typography } from "@mui/material";

const SortByDropdown = ({ sortRule, setSortRule }) => {
    const handleSortDropDown = (event) => {
        setSortRule(event.target.value);
    };

    return (
        <FormControl variant="outlined" size="small">
            <Typography sx={{ color: 'primary.dark' }}>Sort By:</Typography>
            <Select
                value={sortRule}
                onChange={handleSortDropDown}
                sx={{ minWidth: '5rem' }}
            >
                <MenuItem value={"title_asc_rating_asc"}>Title ↑ / Rating ↑</MenuItem>
                <MenuItem value={"title_asc_rating_desc"}>Title ↑ / Rating ↓</MenuItem>
                <MenuItem value={"title_desc_rating_asc"}>Title ↓ / Rating ↑</MenuItem>
                <MenuItem value={"title_desc_rating_desc"}>Title ↓ / Rating ↓</MenuItem>
                <MenuItem value={"rating_asc_title_asc"}>Rating ↑ / Title ↑</MenuItem>
                <MenuItem value={"rating_asc_title_desc"}>Rating ↑ / Title ↓</MenuItem>
                <MenuItem value={"rating_desc_title_asc"}>Rating ↓ / Title ↑</MenuItem>
                <MenuItem value={"rating_desc_title_desc"}>Rating ↓ / Title ↓</MenuItem>
            </Select>
        </FormControl>
    );
};

export default SortByDropdown;
