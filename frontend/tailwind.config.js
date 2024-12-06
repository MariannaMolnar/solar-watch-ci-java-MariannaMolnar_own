/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      backgroundImage: {
        'background-1': "url('/src/assets/background1.png')",
        'background-2':"url('/src/assets/background2.png')",
      }
    },
  },
  plugins: [],
}

