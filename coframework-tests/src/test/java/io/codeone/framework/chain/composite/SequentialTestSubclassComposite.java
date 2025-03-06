package io.codeone.framework.chain.composite;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class SequentialTestSubclassComposite implements Sequential {

    private final List<SequentialTestSubclassComponent> components;
}
