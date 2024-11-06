import React, { useState } from 'react';
import { Box, TextField, Button, InputAdornment, useTheme } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import { useNavigate } from 'react-router-dom';

const SplitSearchBar = ({ sx }) => {
    const theme = useTheme();
    const navigate = useNavigate();
    const [formValues, setFormValues] = useState({
        title: "",
        year: "",
        director: "",
        star: "",
    });

    const handleChange = (e) => {
        setFormValues({ ...formValues, [e.target.name]: e.target.value });
    };

    const handleSearch = () => {
        const { title, year, director, star } = formValues;
        if (!title && !year && !director && !star) {
            console.log('All search fields are empty. No action taken.');
            return;
        }
        navigate('/movielist', { state: formValues });
    };

    const textFieldStyle = {
        "& .MuiInputBase-input": {
            color: theme.palette.primary.dark,
        },
        "& .MuiInputBase-root": {
            backgroundColor: theme.palette.secondary.light,
        },
    };

    const fields = [
        { name: 'title', placeholder: 'Title', width: '30vw', minWidth: '8rem' },
        { name: 'year', placeholder: 'Year', width: '6vw', minWidth: '6rem' },
        { name: 'director', placeholder: 'Director', width: '15vw', minWidth: '15rem' },
        { name: 'star', placeholder: 'Star', width: '15vw', minWidth: '15rem' },
    ];

    return (
        <Box sx={{
            ...sx,
            alignSelf: 'flex-start',
            display: 'flex',
            flexDirection: 'row',
            width: '100%',
            justifyContent: 'center',
            alignItems: 'center',
            gap: '1rem',
        }}>
            {fields.map((field) => (
                <TextField
                    key={field.name}
                    name={field.name}
                    placeholder={field.placeholder}
                    value={formValues[field.name]}
                    onChange={handleChange}
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <SearchIcon />
                            </InputAdornment>
                        ),
                    }}
                    sx={{
                        ...textFieldStyle,
                        width: field.width,
                        minWidth: field.minWidth,
                    }}
                />
            ))}
            <Button
                variant="contained"
                color="primary"
                onClick={handleSearch}
                sx={{
                    fontSize: '1rem',
                    borderRadius: 3
                }}
            >
                Search
            </Button>
        </Box>
    );
};

export default SplitSearchBar;
