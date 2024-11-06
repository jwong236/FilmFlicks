import React from 'react';
import { Box, Typography, Chip } from '@mui/material';
import { useTheme } from '@mui/material/styles';

export default function CharacterList({ characters, onCharacterClick }) {
    const theme = useTheme();
    const chipStyle = {
        cursor: 'pointer',
        color: theme.palette.primary.dark,
        paddingLeft: '1rem',
        paddingRight: '1rem',
        fontSize: '1.2rem',
        backgroundColor: '#F8EFD3',
        '&:hover': {
            backgroundColor: theme.palette.secondary.dark
        }
    };

    return (
        <Box>
            <Box sx={{
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                flexDirection: 'column',
                paddingTop: '2rem',
                paddingBottom: '2rem'
            }}>
                <Typography variant="h3" component='h3' color="primary.dark">
                    Browse by Movie Title
                </Typography>
            </Box>
            <Box sx={{
                display: 'flex',
                flexWrap: 'wrap',
                justifyContent: 'center',
                gap: '1rem',
                padding: '1rem'
            }}>
                {characters.map((character) => (
                    <Chip
                        key={character}
                        label={character}
                        onClick={() => onCharacterClick(character)}
                        sx={chipStyle}
                    />
                ))}
            </Box>
        </Box>
    );
};
