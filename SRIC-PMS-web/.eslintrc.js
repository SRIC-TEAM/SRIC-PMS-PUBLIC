module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    '@vue/typescript/recommended',
    'plugin:vue/vue3-essential',
  ],
  parserOptions: {
    ecmaVersion: 2020
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    "@typescript-eslint/no-explicit-any": ["off"], // 允许使用 any 类型
    "@typescript-eslint/no-unused-vars": ["off"],
  }
}
