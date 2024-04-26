import React from 'react';
import { Link } from 'react-router-dom';
import {
    Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, useTheme
} from '@mui/material';

export default function MovieListTable({ data, handleAdd }) {
    const theme = useTheme();

    const headerStyle = {
        color: 'white'
    };

    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead sx={{backgroundColor: theme.palette.info.main}}>
                    <TableRow>
                        <TableCell sx={headerStyle}>Title</TableCell>
                        <TableCell sx={headerStyle} align="right">Year</TableCell>
                        <TableCell sx={headerStyle} align="right">Director</TableCell>
                        <TableCell sx={headerStyle} align="right">Genres</TableCell>
                        <TableCell sx={headerStyle} align="right">Stars</TableCell>
                        <TableCell sx={headerStyle} align="right">Rating</TableCell>
                        <TableCell sx={headerStyle} align="center">Cart</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody sx={{ backgroundColor: theme.palette.secondary.light }}>
                    {data.map((movie, index) => (
                        <TableRow key={index}>
                            <TableCell component="th" scope="row">
                                <Link to={`/singlemovie?title=${encodeURIComponent(movie.title)}`} style={{ color: theme.palette.primary.main, textDecoration: 'none' }}>
                                    {movie.title}
                                </Link>
                            </TableCell>
                            <TableCell align="right">{movie.year}</TableCell>
                            <TableCell align="right">{movie.director}</TableCell>
                            <TableCell align="right">{movie.genres.join(', ')}</TableCell>
                            <TableCell align="right">
                                {movie.stars.map((star, idx) => (
                                    <React.Fragment key={idx}>
                                        <Link to={`/singlestar?name=${encodeURIComponent(star)}`} style={{ color: theme.palette.primary.main, textDecoration: 'none' }}>
                                            {star}
                                        </Link>
                                        {idx < movie.stars.length - 1 ? ', ' : ''}
                                    </React.Fragment>
                                ))}
                            </TableCell>
                            <TableCell align="right">{movie.rating}</TableCell>
                            <TableCell align="right">
                                <Button
                                    sx={{
                                        backgroundColor: theme.palette.primary.main,
                                        color: theme.palette.secondary.light,
                                        '&:hover': {
                                            backgroundColor: theme.palette.primary.dark
                                        }
                                    }}
                                    onClick={() => handleAdd(movie)}>
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
