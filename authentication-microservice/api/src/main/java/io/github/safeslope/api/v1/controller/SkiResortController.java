package io.github.safeslope.api.v1.controller;

import io.github.safeslope.api.v1.dto.SkiResortDto;
import io.github.safeslope.api.v1.mapper.SkiResortMapper;
import io.github.safeslope.skiresort.service.SkiResortService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ski-resorts")
public class SkiResortController {

    private final SkiResortService skiResortService;
    private final SkiResortMapper skiResortMapper;

    public SkiResortController(SkiResortService skiResortService, SkiResortMapper skiResortMapper) {
        this.skiResortService = skiResortService;
        this.skiResortMapper = skiResortMapper;
    }

    @GetMapping
    public List<SkiResortDto> list() {
        return skiResortMapper.toDtoList(skiResortService.getAll());
    }

    @GetMapping("/{id}")
    public SkiResortDto get(@PathVariable Integer id) {
        return skiResortMapper.toDto(skiResortService.get(id));
    }

    @GetMapping("/name/{name}")
    public SkiResortDto getByName(@PathVariable String name) {
        return skiResortMapper.toDto(skiResortService.getByName(name));
    }

    @PostMapping
    public SkiResortDto create(@RequestBody SkiResortDto dto) {
        return skiResortMapper.toDto(skiResortService.create(skiResortMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public SkiResortDto update(@PathVariable Integer id, @RequestBody SkiResortDto dto) {
        return skiResortMapper.toDto(skiResortService.update(id, skiResortMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        skiResortService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
