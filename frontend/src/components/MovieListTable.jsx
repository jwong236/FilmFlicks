import React from 'react';
import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button, useTheme} from '@mui/material';

function DataTable({ data, handleAdd}) {
    const theme = useTheme();

    const headerStyle = {
        color: 'white'
    }

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
                            <TableCell component="th" scope="row">{movie.title}</TableCell>
                            <TableCell align="right">{movie.year}</TableCell>
                            <TableCell align="right">{movie.director}</TableCell>
                            <TableCell align="right">{movie.genres.join(', ')}</TableCell>
                            <TableCell align="right">{movie.stars.join(', ')}</TableCell>
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

export default DataTable;
