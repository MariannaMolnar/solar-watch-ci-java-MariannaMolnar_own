const InputField = ({
    type = "text",
    label,
    onChange,
    value,
    placeholder,
    id,
    ...props
  }) => (
    <div className="mb-4 max-w-lg flex flex-col">
      {label && (
        <label htmlFor={id} className="block  font-medium mb-2 text-left">
          {label}
        </label>
      )}
      <input
        id={id}
        type={type}
        onChange={onChange}
        value={value}
        placeholder={placeholder}
        className="border border-gray-300 rounded-md px-4 py-2"
        {...props}
      />
    </div>
  );
  
  export default InputField;