import React, { useState } from 'react';
import { Box, Typography } from '@mui/material';
import DatabaseAddCard from './DatabaseAddCard.jsx';
import DashboardHistoryTable from './DashboardHistoryTable.jsx';
import DashboardMetadataTable from './DashboardMetadataTable.jsx';

const DashboardMainCard = ({ employeeName }) => {
    const [history, setHistory] = useState([]);

    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'row',
                backgroundColor: 'info.light',
                borderRadius: '20px',
                padding: '1.5rem',
                height: '85vh',
                width: '70vw',
                overflowY: 'auto',
                gap: '1.5rem',
                boxShadow: 3,
            }}
        >
            {/* Left Column: Add Card and History Table */}
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    flex: 3,
                    height: '100%',
                }}
            >
                <Typography
                    variant="h3"
                    sx={{ color: 'primary.dark', textAlign: 'center', marginBottom: '1rem' }}
                >
                    {employeeName}'s Dashboard
                </Typography>
                <Box sx={{
                    maxHeight: '63%'
                }}>
                    <DatabaseAddCard setHistory={setHistory} />
                </Box>


                {/* History Table */}
                <Box
                    sx={{
                        marginTop: '1rem',
                        flex: 1,
                        overflowY: 'auto',
                    }}
                >
                    <DashboardHistoryTable history={history} />
                </Box>
            </Box>

            {/* Right Column: Metadata Table */}
            <Box
                sx={{
                    flex: 1,
                    display: 'flex',
                    flexDirection: 'column',
                    height: '100%',
                    overflowY: 'auto',
                }}
            >
                {/* Metadata Table */}
                <Box
                    sx={{
                        flex: 1,
                        backgroundColor: 'background.paper',
                        borderRadius: '8px',
                        padding: '1rem',
                    }}
                >
                    <DashboardMetadataTable />
                </Box>
            </Box>
        </Box>
    );
};

export default DashboardMainCard;
