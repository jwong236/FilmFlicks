import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useTheme, Typography } from "@mui/material";
import SplitSearchBar from '../components/components(deprecated)/SplitSearchBar.jsx';
import { useGenres } from '../hooks/homepage/useGenres.jsx';
import GenreList from '../components/homepage/GenreList.jsx';
import CharacterList from "../components/homepage/CharacterList.jsx";
import PageLayout from '../components/common/PageLayout.jsx';
import { CHARACTERS } from '../utils/constants.js';
import FullHeightContainer from "../components/common/FullHeightContainer.jsx";

export default function Homepage() {
    const navigate = useNavigate();
    const theme = useTheme();
    const { genres, error } = useGenres(navigate);

    const handleNavigate = (type, value) => {
        navigate('/movielist', { state: { [type]: value } });
    };

    return (
        <PageLayout>
            <FullHeightContainer
                sx={{
                    display: 'flex',
                    width: '95vw',
                    height: '85vh',
                    backgroundColor: theme.palette.info.light,
                    borderRadius: '20px',
                    flexDirection: 'column',
                    alignItems: 'center',
                    paddingTop: '1rem',
                }}
            >
                <Typography variant="h3" component='h3' color="primary.dark">
                    Advanced Search
                </Typography>
                <SplitSearchBar />
                <GenreList genres={genres} onGenreClick={(genre) => handleNavigate('genre', genre)} />
                <CharacterList characters={CHARACTERS} onCharacterClick={(character) => handleNavigate('character', character)} />
            </FullHeightContainer>
        </PageLayout>
    );
}
