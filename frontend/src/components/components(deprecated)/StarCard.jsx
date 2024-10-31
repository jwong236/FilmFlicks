import { Box, Card, CardHeader, CardContent, Typography, Chip, Link as MuiLink } from '@mui/material';
import { Link as RouterLink } from 'react-router-dom';
import empty_avatar from '../../assets/empty-avatar.png'

export default function StarCard(props) {
    const darkerShade = '#1a1a1a';
    const lighterShade = '#4B4B4D';

    return (
        <Box sx={{
            display: 'flex', justifyContent: 'center', alignItems: 'center', padding: '4px', width: '100%', height: '100%',
            borderRadius: '1rem',
            backgroundColor: 'info.light',
            transition: 'background-color .4s ease-in-out',
            '&:hover': {
                backgroundColor: 'info.dark'
            }
        }}>
            <Card sx={{
                display: 'flex',
                flexDirection: 'column',
                background: `radial-gradient(circle at 80% 60%, ${lighterShade}, ${darkerShade} 90%)`,
                color: 'secondary.main',
                width: '100%',
                height: '100%',
                borderRadius: '0.75rem',
                boxShadow: '0 4px 10px 0 rgba(0,0,0,.25)',
                padding: '1rem',
            }}>
                <Typography variant="h3" color="secondary.main" sx={{ textAlign: 'center', mb: 2 }}>
                    STAR CARD
                </Typography>
                <Typography variant="h5" color="secondary.main" sx={{ textAlign: 'center', mb: 1 }}>
                    NAME: {props.name}
                </Typography>
                <Typography variant="h5" color="secondary.main" sx={{ textAlign: 'center', mb: 2 }}>
                    DOB: {props.birthYear}
                </Typography>
                <Box sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    flexWrap: 'wrap',
                    alignItems: 'center',
                    padding: '1rem',
                }}>
                    <Box sx={{
                        flex: '1'
                    }}>
                        <img
                            src={empty_avatar}
                            style={{
                                width: '100%',
                                height: '100%',
                                objectFit: 'cover'
                            }}
                        />
                    </Box>
                    <Box sx={{
                        display: 'flex',
                        flexDirection: 'row',
                        flexWrap: 'wrap',
                    }}>
                        {props.movieTitles.map((title, index) => (
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
                                sx={{ bgcolor: '#121212', margin: '.5rem' }}
                            />
                        ))}
                    </Box>
                </Box>

            </Card>
        </Box>
    );
}