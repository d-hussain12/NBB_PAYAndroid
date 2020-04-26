

package io.token.security;

import io.token.proto.common.security.SecurityProtos;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_SecretKey extends SecretKey {

  private final String id;

  private final SecurityProtos.Key.Level level;

  private final PublicKey publicKey;

  private final PrivateKey privateKey;

  private final Long expiresAtMs;

  AutoValue_SecretKey(
      String id,
      SecurityProtos.Key.Level level,
      PublicKey publicKey,
      PrivateKey privateKey,
      @Nullable Long expiresAtMs) {
    if (id == null) {
      throw new NullPointerException("Null id");
    }
    this.id = id;
    if (level == null) {
      throw new NullPointerException("Null level");
    }
    this.level = level;
    if (publicKey == null) {
      throw new NullPointerException("Null publicKey");
    }
    this.publicKey = publicKey;
    if (privateKey == null) {
      throw new NullPointerException("Null privateKey");
    }
    this.privateKey = privateKey;
    this.expiresAtMs = expiresAtMs;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public SecurityProtos.Key.Level getLevel() {
    return level;
  }

  @Override
  public PublicKey getPublicKey() {
    return publicKey;
  }

  @Override
  public PrivateKey getPrivateKey() {
    return privateKey;
  }

  @Nullable
  @Override
  public Long getExpiresAtMs() {
    return expiresAtMs;
  }

  @Override
  public String toString() {
    return "SecretKey{"
         + "id=" + id + ", "
         + "level=" + level + ", "
         + "publicKey=" + publicKey + ", "
         + "privateKey=" + privateKey + ", "
         + "expiresAtMs=" + expiresAtMs
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof SecretKey) {
      SecretKey that = (SecretKey) o;
      return (this.id.equals(that.getId()))
           && (this.level.equals(that.getLevel()))
           && (this.publicKey.equals(that.getPublicKey()))
           && (this.privateKey.equals(that.getPrivateKey()))
           && ((this.expiresAtMs == null) ? (that.getExpiresAtMs() == null) : this.expiresAtMs.equals(that.getExpiresAtMs()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= id.hashCode();
    h$ *= 1000003;
    h$ ^= level.hashCode();
    h$ *= 1000003;
    h$ ^= publicKey.hashCode();
    h$ *= 1000003;
    h$ ^= privateKey.hashCode();
    h$ *= 1000003;
    h$ ^= (expiresAtMs == null) ? 0 : expiresAtMs.hashCode();
    return h$;
  }

}
