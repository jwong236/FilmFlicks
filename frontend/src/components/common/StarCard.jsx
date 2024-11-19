import { Box, Card, Typography, Chip, Link as MuiLink } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import { useSingleStarPageHooks } from '../../hooks/useSingleStarPageHooks.jsx';
import empty_avatar from '../../assets/empty-avatar.png';

export default function StarCard({ sx = {} }) {
    const darkerShade = '#1a1a1a';
    const lighterShade = '#4B4B4D';
    const { starData } = useSingleStarPageHooks();

    return (
        <Box sx={{
            ...sx,
            display: 'flex', justifyContent: 'center', alignItems: 'center', padding: '4px',
            borderRadius: '1rem',
            backgroundColor: 'info.light',
            transition: 'background-color .4s ease-in-out',
            '&:hover': {
                backgroundColor: 'info.dark'
            },
        }}>
            <Card sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                background: `radial-gradient(circle at 80% 60%, ${lighterShade}, ${darkerShade} 90%)`,
                color: 'secondary.main',
                borderRadius: '0.75rem',
                boxShadow: '0 4px 10px 0 rgba(0,0,0,.25)',
                padding: '1rem',
            }}>
                <Typography variant="h4" color="secondary.main" sx={{textAlign: 'center', mb: 2}}>
                    STAR CARD
                </Typography>
                <Typography variant="h6" color="secondary.main" sx={{textAlign: 'center', mb: 1}}>
                    NAME: {starData.name}
                </Typography>
                <Typography variant="h6" color="secondary.main" sx={{textAlign: 'center', mb: 2}}>
                    DOB: {starData.birth_year}
                </Typography>
                <img
                    src={empty_avatar}
                    alt="Star Avatar"
                    style={{
                        width: '80%',
                    }}
                />
                <Box sx={{
                    display: 'flex',
                    flexDirection: 'row',
                    flexWrap: 'wrap',
                    justifyContent: 'center',
                }}>
                    {starData.movies.map((title, index) => (
                        <Chip
                            key={index}
                            label={
                                <MuiLink
                                    component={RouterLink}
                                    to={`/singlemovie?title=${encodeURIComponent(title)}`}
                                    sx={{
                                        textDecoration: 'none',
                                        color: 'secondary.main',
                                        '&:hover': {
                                            textDecoration: 'underline',
                                        }
                                    }}
                                >
                                    {title}
                                </MuiLink>
                            }
                            sx={{bgcolor: '#121212', margin: '.5rem'}}
                        />
                    ))}
                </Box>
            </Card>
        </Box>
    );
}
