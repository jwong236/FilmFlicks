import React from 'react';
import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Box, Typography} from '@mui/material';

const DashboardHistoryTable = ({ history }) => {
    const formatId = (id) => id === 'null' || id === 0 ? '-' : id;

    return (
        <Box>
            <Typography variant="h6" gutterBottom component="div" sx={{color: 'primary.dark'}}>
                History
            </Typography>
            <TableContainer component={Paper} sx={{maxHeight: '350px', width: '750px', overflow: 'auto'}}>
                <Table stickyHeader aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Message</TableCell>
                            <TableCell align="right">Movie ID</TableCell>
                            <TableCell align="right">Star ID</TableCell>
                            <TableCell align="right">Genre ID</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {history.map((row, index) => (
                            <TableRow key={index}>
                                <TableCell component="th" scope="row">
                                    {row.message}
                                </TableCell>
                                <TableCell align="right">{formatId(row.newMovieId)}</TableCell>
                                <TableCell align="right">{formatId(row.newStarId)}</TableCell>
                                <TableCell align="right">{formatId(row.newGenreId)}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>

    );
};

export default DashboardHistoryTable;
