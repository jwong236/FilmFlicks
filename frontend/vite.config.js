import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  server: {
    host: 'local', // Only bind to localhost
    port: 5173, // Specify the port
  },
  plugins: [react()],
  optimizeDeps: {
    include: [
      '@mui/material',
      '@emotion/react',
      '@emotion/styled'
    ]
  }
});

