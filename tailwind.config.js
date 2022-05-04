module.exports = {
  content: ["./src/**/*.html"],
  theme: {
    fontFamily: {
      sans: ['Poppins'],
    },
    extend: {},
  },
  plugins: [require('@tailwindcss/forms')],
}
