import React from 'react';
import { Box, TextField, Button, InputAdornment } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

const AdvancedSearchBar = ({ searchQuery, setSearchQuery, handleSearch, theme }) => {
    const textFieldStyle = {
        "& .MuiInputBase-input": {
            color: theme.palette.primary.dark,
        },
        "& .MuiInputBase-root": {
            backgroundColor: theme.palette.secondary.light,
        },
    };

    return (
        <Box sx={{
            alignSelf: 'flex-start',
            display: 'flex',
            flexDirection: 'row',
            width: '100%',
            paddingTop: '1rem',
            paddingBottom: '1rem',
            justifyContent: 'center',
            alignItems: 'center',
            gap: '1rem',
        }}>
            <TextField
                placeholder="Search for movies..."
                value={searchQuery}
                onChange={e => setSearchQuery(e.target.value)}
                InputProps={{
                    startAdornment: (
                        <InputAdornment position="start">
                            <SearchIcon />
                        </InputAdornment>
                    ),
                }}
                sx={{
                    ...textFieldStyle,
                    width: '40vw',
                    minWidth: '10rem'
                }}
            />
            <Button
                onClick={handleSearch}
                sx={{
                    backgroundColor: '#FF907E',
                    color: 'secondary.light',
                    fontWeight: 'bold',
                    fontSize: '1rem',

                    '&:hover': {
                        backgroundColor: 'primary.main',
                        transform: 'scale(1.05)'
                    }
                }}>
                Search
            </Button>
        </Box>
    );
};

export default AdvancedSearchBar;
