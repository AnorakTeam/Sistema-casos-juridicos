import React from 'react';
import { useFormContext } from 'react-hook-form';

export function FormCheckbox({ name, label, ...props }) {
  const { register, formState: { errors } } = useFormContext();

  return (
    <div className="flex flex-col gap-1 w-full">
      <div className="flex items-center gap-2">
        <input
          id={name}
          type="checkbox"
          {...register(name)}
          {...props}
          className={`h-4 w-4 rounded border-input bg-transparent text-primary focus:ring-ring ${
            errors[name] ? 'border-red-500' : ''
          }`}
        />
        {label && <label htmlFor={name} className="text-sm font-medium leading-none cursor-pointer">{label}</label>}
      </div>
      {errors[name] && <p className="text-xs text-red-500">{errors[name]?.message}</p>}
    </div>
  );
}
