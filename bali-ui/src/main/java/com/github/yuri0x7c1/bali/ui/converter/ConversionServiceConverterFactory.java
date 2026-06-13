package com.github.yuri0x7c1.bali.ui.converter;

import java.util.Collection;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Component;

import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;

@Component
public class ConversionServiceConverterFactory {

	private final ConversionService conversionService;
 	public ConversionServiceConverterFactory(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	public <P, M> Converter<P, M> create(Class<P> presentationClass, Class<M> modelClass) {
		return new Converter<P, M>() {

			@Override
			public Result<M> convertToModel(P value, ValueContext context) {
				return Result.ok(conversionService.convert(value, modelClass));
			}

			@Override
			public P convertToPresentation(M value, ValueContext context) {
				return conversionService.convert(value, presentationClass);
			}
		};
	}

	public <P, M, PC extends Collection<P>, MC extends Collection<M>> Converter<PC, MC> create(
			Class<P> presentationClass, Class<M> modelClass,
			Class<?> presentationCollectionClass,
			Class<?> modelCollectionClass) {
		return new Converter<PC, MC>() {

			@Override
			public Result<MC> convertToModel(PC value, ValueContext context) {
				TypeDescriptor sourceType = TypeDescriptor.collection(presentationCollectionClass, TypeDescriptor.valueOf(presentationClass));
				TypeDescriptor targetType = TypeDescriptor.collection(modelCollectionClass, TypeDescriptor.valueOf(modelClass));
				return Result.ok((MC) conversionService.convert(value, sourceType, targetType));
			}

			@Override
			public PC convertToPresentation(MC value, ValueContext context) {
				TypeDescriptor sourceType = TypeDescriptor.collection(modelCollectionClass, TypeDescriptor.valueOf(modelClass));
				TypeDescriptor targetType = TypeDescriptor.collection(presentationCollectionClass, TypeDescriptor.valueOf(presentationClass));
				return (PC)conversionService.convert(value, sourceType, targetType);
			}
		};
	}

	public <P, M, PC extends Collection<P>, MC extends Collection<M>> Converter<PC, MC> create(
			Class<P> presentationClass, Class<M> modelClass, Class<?> collectionClass) {
		return create(presentationClass, modelClass, collectionClass, collectionClass);
	}
}
