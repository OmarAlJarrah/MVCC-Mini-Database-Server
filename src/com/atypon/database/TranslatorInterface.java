package com.atypon.database;

import com.atypon.api.DatabaseRequestInterface;

public interface TranslatorInterface {
  Object translate(DatabaseRequestInterface request);
}
