import React, { useEffect, useState } from 'react';
import { Box, Typography, List, ListItem, ListItemText } from "@mui/material";

const URL = import.meta.env.VITE_BACKEND_URL;

export default function DatabaseMetadataViewer() {
    const [metadata, setMetadata] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchMetadata = async () => {
            try {
                const response = await fetch(`${URL}/metadata/database-metadata`, {
                    method: 'GET',
                    credentials: 'include'
                });

                if (response.ok) {
                    const data = await response.json();
                    setMetadata(data);
                } else {
                    setError('Failed to fetch database metadata.');
                }
            } catch (err) {
                setError('Error fetching database metadata.');
                console.error('Error:', err);
            }
        };

        fetchMetadata();
    }, []);

    if (error) {
        return (
            <Typography color="error" variant="h6" align="center">
                {error}
            </Typography>
        );
    }

    return (
        <Box
            sx={{
                borderRadius: '8px',
                overflowY: 'auto',
                boxShadow: 1,
            }}
        >
            <Typography variant="h4" align="center" sx={{ marginBottom: '1rem' }}>
                Database Table Structure
            </Typography>
            {metadata.length === 0 ? (
                <Typography variant="h6" align="center">
                    Loading...
                </Typography>
            ) : (
                metadata.map((table) => (
                    <Box key={table.tableName} sx={{ marginBottom: '2rem' }}>
                        <Typography variant="h5" sx={{ marginBottom: '0.5rem' }}>
                            {table.tableName}
                        </Typography>
                        <List>
                            {table.columns.map((column) => (
                                <ListItem key={column.columnName}>
                                    <ListItemText
                                        primary={`Column: ${column.columnName}`}
                                        secondary={`Data Type: ${column.dataType}`}
                                    />
                                </ListItem>
                            ))}
                        </List>
                    </Box>
                ))
            )}
        </Box>
    );
}
