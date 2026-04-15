import React from 'react';
import { useFormContext } from 'react-hook-form';

export function FormSelect({ name, label, options, ...props }) {
  const { register, formState: { errors } } = useFormContext();

  return (
    <div className="flex flex-col gap-1.5 w-full">
      {label && <label htmlFor={name} className="text-sm font-medium leading-none">{label}</label>}
      <select
        id={name}
        {...register(name)}
        {...props}
        className={`flex h-8 w-full rounded-lg border border-input bg-transparent px-2.5 py-1 text-base transition-colors outline-none focus-visible:border-ring focus-visible:ring-3 focus-visible:ring-ring/50 disabled:cursor-not-allowed disabled:opacity-50 md:text-sm ${
          errors[name] ? 'border-red-500' : ''
        }`}
      >
        <option value="">Seleccione una opción</option>
        {options.map((opt) => (
          <option key={opt.value} value={opt.value}>
            {opt.label}
          </option>
        ))}
      </select>
      {errors[name] && <p className="text-xs text-red-500">{errors[name]?.message}</p>}
    </div>
  );
}
