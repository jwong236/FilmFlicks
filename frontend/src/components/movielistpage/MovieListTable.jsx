import React from 'react';
import { Link } from 'react-router-dom';
import {
    Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, useTheme
} from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function MovieListTable({ data, handleAdd }) {
    const theme = useTheme();
    const navigate = useNavigate();

    const handleGenreClick = (genre) => {
        navigate('/movielist', { state: { genre } });
    };

    return (
        <TableContainer component={Paper} sx={{ margin: '1rem' }}>
            <Table>
                <TableHead sx={{ backgroundColor: theme.palette.info.main }}>
                    <TableRow>
                        {['Title', 'Year', 'Director', 'Genres', 'Stars', 'Rating', 'Cart'].map((header) => (
                            <TableCell key={header} align={header === 'Title' ? 'left' : 'right'} sx={{ color: 'white' }}>
                                {header}
                            </TableCell>
                        ))}
                    </TableRow>
                </TableHead>
                <TableBody sx={{ backgroundColor: theme.palette.secondary.light }}>
                    {data.map((movie) => (
                        <TableRow key={movie.id}>
                            <TableCell>
                                <Link to={`/singlemovie?title=${encodeURIComponent(movie.title)}`} style={{ color: theme.palette.primary.main, textDecoration: 'none' }}>
                                    {movie.title}
                                </Link>
                            </TableCell>
                            <TableCell align="right">{movie.year}</TableCell>
                            <TableCell align="right">{movie.director}</TableCell>
                            <TableCell align="right">
                                {movie.genres.map((genre, index) => (
                                    <React.Fragment key={genre.id}>
                                        <span
                                            onClick={() => handleGenreClick(genre.name)}
                                            style={{ cursor: 'pointer', color: 'blue', textDecoration: 'underline' }}
                                        >
                                            {genre.name}
                                        </span>
                                        {index < movie.genres.length - 1 ? ', ' : ''}
                                    </React.Fragment>
                                ))}
                            </TableCell>
                            <TableCell align="right">
                                {movie.stars.map((star, index) => (
                                    <React.Fragment key={star.id}>
                                        <Link to={`/singlestar?name=${encodeURIComponent(star.name)}`} style={{ color: theme.palette.primary.main, textDecoration: 'none' }}>
                                            {star.name}
                                        </Link>
                                        {index < movie.stars.length - 1 ? ', ' : ''}
                                    </React.Fragment>
                                ))}
                            </TableCell>
                            <TableCell align="right">{movie.rating ?? 'N/A'}</TableCell>
                            <TableCell align="center">
                                <Button
                                    sx={{
                                        backgroundColor: theme.palette.primary.main,
                                        color: theme.palette.secondary.light,
                                        '&:hover': {
                                            backgroundColor: theme.palette.primary.dark
                                        }
                                    }}
                                    onClick={() => handleAdd(movie)}
                                >
                                    Add
                                </Button>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}